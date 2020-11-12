import React, {useState, useEffect} from "react";
import axios from "axios";

import User from "./User";

const UserList = () => {

  const [users, setUsers] = useState([]);

  const fetchUsers = () => {
    axios.get("/api/users")
      .then(res => {
        console.log(res);
        setUsers(res.data);
      });
  }

  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <div className="user-list">
    {
      users.map((user, i) => {
        return <User key={i} { ...user} />;
      })
    }
    </div>
  )
}

export default UserList;
