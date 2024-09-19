import { useEffect, useState } from "react";
import axiosInstance from "../../axiosInstance";
import { Container, Row } from "react-bootstrap";
import Contest from "./Contest";

const defaultPagination = {
  page: 1,
  limit: 5,
  sortBy: "title",
  sortDesc: "true",
};

const Contests = () => {
  const [contestsArray, setContestsArray] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    axiosInstance
      .get("/api/v1/contests")
      .then((data) => {
        console.log(data);
        setContestsArray(data.data.content);
        setIsLoading(false);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  if (isLoading) {
    return <div>DATA IS LOADING</div>;
  }

  return (
    <>
      <Container>
        <Row>
          {contestsArray.map((contest) => {
            return <Contest key={contest.id} contest={contest} />;
          })}
        </Row>
      </Container>
    </>
  );
};
export default Contests;
