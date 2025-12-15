db = db.getSiblingDB('gameplay');

db.createUser({
    user: "user_mongodb",
    pwd: "password_mongodb",
    roles: [
        { role: "readWrite", db: "gameplay" }
    ]
});

