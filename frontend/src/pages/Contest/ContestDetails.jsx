import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Button, Card, Col, Container, Row } from "react-bootstrap";
import axiosInstance from "../../axiosInstance";
import Category from "../Category/Category";

const ContestDetails = () => {
  const [contest, setContest] = useState();
  const [isLoading, setIsLoading] = useState(true);
  const { id } = useParams();
  //   const onEditAdvertisementButton = useNavigate();

  useEffect(() => {
    axiosInstance
      .get(`/api/v1/contests/${id}`)
      .then((data) => {
        console.log("add desponse: ", data);
        setContest(data.data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [id]);

  const deleteContest = (id) => {
    console.log("contest id", contest.id);

    axiosInstance
      .delete(`/api/v1/contests/${id}`)
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

  const { name, description, startDate, endDate, categories } = contest;

  return (
    <>
      <Card style={{ margin: "10vh" }}>
        {/* <Col >
                        <img src={image} alt="picture" />
                    </Col> */}
        <h5>Title</h5>
        <p>{name}</p>
        <p style={{ color: "red" }}>Description: </p> <p>{description}</p>
        <p>Start date {startDate}</p>
        <p>End date {endDate}</p>
        <Container className="bg-dark text-white">
            <h1 className="light">categories</h1>
          <Row>
            {categories.map((category) => (
              <Category key={category.id} category={category}/>
            ))}
          </Row>
        </Container>

        {/* <Button variant="warning" onClick={()=>onEditAdvertisementButton("/editAdd/", {state: advertisement})}>Edit Addvertisement</Button> */}
        <Button variant="danger" onClick={() => deleteContest(id)}>
          Delete
        </Button>
      </Card>

    </>
  );
};
export default ContestDetails;
