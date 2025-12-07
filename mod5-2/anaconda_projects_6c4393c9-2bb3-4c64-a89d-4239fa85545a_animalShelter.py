# animalShelter.py
from pymongo import MongoClient
from typing import Optional, Dict, Any, List
from datetime import datetime
from typing import Optional, List, Dict, Any


class AnimalShelter:
    """CRUD operations for the AAC.animal collection in MongoDB."""

    def __init__(
        self,
        username: Optional[str] = None,
        password: Optional[str] = None,
        *,
        host: str = "localhost",
        port: int = 27017,
        db: str = "AAC",
        col: str = "animals",
        uri: Optional[str] = None,
        server_selection_timeout_ms: int = 10000,
    ):
        # Prefer explicit URI if provided (e.g., Atlas mongodb+srv://...)
        if uri:
            self.client = MongoClient(uri, serverSelectionTimeoutMS=server_selection_timeout_ms)
        else:
            # If BOTH username and password are provided, use auth; otherwise connect without auth (local default)
            if username and password:
                self.client = MongoClient(
                    host=host,
                    port=port,
                    username=username,
                    password=password,
                    authSource=db,
                    serverSelectionTimeoutMS=server_selection_timeout_ms,
                )
            else:
                self.client = MongoClient(
                    host=host,
                    port=port,
                    serverSelectionTimeoutMS=server_selection_timeout_ms,
                )

        self.database = self.client[db]
        self.collection = self.database[col]

    # C — Create
    def create(self, data: Dict[str, Any]) -> bool:
        if not data:
            raise ValueError("Nothing to save; 'data' is empty.")
        result = self.collection.insert_one(data)
        return result.acknowledged

    # R — Read
    def read(self, criteria: Optional[Dict[str, Any]] = None) -> List[Dict[str, Any]]:
        q = criteria or {}
        # Exclude _id so DataTable/CSV remain clean; return list for pandas
        return list(self.collection.find(q, {"_id": False}))

    # U — Update
    def update(self, initial: Dict[str, Any], change: Dict[str, Any]) -> Dict[str, Any]:
        if not initial:
            raise ValueError("Nothing to update; 'initial' is empty.")
        if self.collection.count_documents(initial, limit=1) == 0:
            return {"matched": 0, "modified": 0, "note": "No document matched the criteria."}
        r = self.collection.update_many(initial, {"$set": change})
        return {"matched": r.matched_count, "modified": r.modified_count}

    # D — Delete
    def delete(self, remove: Dict[str, Any]) -> Dict[str, Any]:
        if not remove:
            raise ValueError("Nothing to delete; 'remove' is empty.")
        if self.collection.count_documents(remove, limit=1) == 0:
            return {"deleted": 0, "note": "No document matched the criteria."}
        r = self.collection.delete_many(remove)
        return {"deleted": r.deleted_count}


    def stats_by_outcome_breed(
        self,
        from_iso: Optional[str] = None,
        to_iso: Optional[str] = None,
        limit: int = 50
    ) -> List[Dict[str, Any]]:
        match: Dict[str, Any] = {}
        #CSV/DB uses "datetime" column; handle both date string or real Date
        if from_iso or to_iso:
            date_range: Dict[str, Any] = {}
            if from_iso:
                date_range["$gte"] = from_iso
            if to_iso:
                date_range["$lte"] = to_iso
            match["datetime"] = date_range

        pipeline = [
            {"$match": match} if match else {"$match": {}},
            {
                "$group": {
                    "_id": {"outcome": "$outcome_type", "breed": "$breed"},
                    "count": {"$sum": 1},
                }
            },
            {"$sort": {"count": -1}},
            {"$limit": limit},
            {
                "$project": {
                    "_id": 0,
                    "outcome_type": "$_id.outcome",
                    "breed": "$_id.breed",
                    "count": 1
                }
            }
        ]
        return list(self.collection.aggregate(pipeline))
