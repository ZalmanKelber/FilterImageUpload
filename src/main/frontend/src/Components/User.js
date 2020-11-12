import React from "react";

import Dropzone from "./Dropzone";

const User = ({username, userId}) => {
  return (
    <div className="user">
      <Dropzone username={username} userId={userId} />
      <p>{userId}</p>
      <img src={`/api/users/${userId}/image/download`} />
    </div>
  );

}

export default User;
