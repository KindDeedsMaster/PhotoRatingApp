import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Button, Card, Col, Container, Row } from "react-bootstrap";
import axiosInstance from "../../axiosInstance";
import PhotoGallary from "../TestPhoto/PhotoGallary";


const CategoryDetails = () => {
  const [category, setCategory] = useState();
  const [isLoading, setIsLoading] = useState(true);
  const { id } = useParams();
  //   const onEditAdvertisementButton = useNavigate();

  useEffect(() => {
    axiosInstance
      .get(`/api/v1/category/${id}`)
      .then((data) => {
        console.log("add desponse: ", data);
        setCategory(data.data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [id]);

  const deleteCategory = (id) => {
    console.log("category id", category.id);

    axiosInstance
      .delete(`/api/v1/category/${id}`)
      .then((response) => {
        console.log("delete response", response.data);
        alert("deleted successfully");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  if (isLoading) {
    return <div>DATA IS LOADING</div>;
  }

  const { name, description, type} = category;

  return (
    <>
      <Card style={{ margin: "10vh" }}>
        {/* <Col >
                        <img src={image} alt="picture" />
                    </Col> */}
        <h5>Title</h5>
        <p>{name}</p>
        <p style={{ color: "red" }}>Description: </p> <p>{description}</p>
        <p>Type: {type}</p>
        

        {/* <Button variant="warning" onClick={()=>onEditAdvertisementButton("/editAdd/", {state: advertisement})}>Edit Addvertisement</Button> */}
        <Button variant="danger" onClick={() => deleteCategory(id)}>
          Delete
        </Button>
      </Card>
      <PhotoGallary categoryId={id}/>

    </>
  );
};
export default CategoryDetails;
