import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const PhotoGallary = ({categoryId}) => {
//   const { categoryId } = useParams();
  const [imageUrls, setImageUrls] = useState([]);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/category/${categoryId}/images`)
      .then((response) => {
        console.log(response);
        setImageUrls(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [categoryId]);

  return (
    <div>
      <h1>Image Gallery</h1>
      <div style={{ display: "flex", flexWrap: "wrap" }}>
        {imageUrls.map((url, index) => (
          <div key={index} style={{ margin: "10px" }}>
            <img
              src={url}
              alt={`Image ${index}`}
              style={{ width: "200px", height: "200px", objectFit: "cover" }}
            />
          </div>
        ))}
      </div>
    </div>
  );
};
export default PhotoGallary;
