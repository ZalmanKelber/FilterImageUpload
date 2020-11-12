import React, {useCallback} from 'react';
import {useDropzone} from 'react-dropzone';
import axios from "axios";

const Dropzone = ({username, userId}) => {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);
    const formData = new FormData();
    formData.append("file", file);
    axios.post(
              `/api/users/${userId}/image/upload`,
              formData,
              {
                headers: {
                  "Content-Type": "multipart/form-data"
                }
              }
          ).then(console.log("file uploaded"))
          .catch(err => console.log(err));

  }, []);

  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <h2>{username}</h2> :
          <h3>{username}</h3>
      }
    </div>
  )
}

export default Dropzone;
