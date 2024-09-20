import { useEffect, useState } from "react";
import axiosInstance from "../../axiosInstance";
import { Container, Row } from "react-bootstrap";

const Categories = () => {
  const [categoriesArray, setCategoriesArray] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    axiosInstance.get(`/api/v1/contests/${contestId}/category`).then((data) => {
      console.log(data);
      setCategoriesArray(data.data.content);
      setIsLoading(false);
    }).catch((error)=>{
        console.log(error);
    });
  },[]);

  if (isLoading) {
    return <div>DATA IS LOADING</div>;
  }
  return (
    <>
      <Container>
        <Row>
          {categoriesArray.map((category) => {
            return <Category key={category.id} category={category} />;
          })}
        </Row>
      </Container>
    </>
  );
};
export default Categories;
