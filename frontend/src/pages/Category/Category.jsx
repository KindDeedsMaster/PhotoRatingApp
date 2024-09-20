import { Button, Card, Col } from "react-bootstrap";

const Category = ({category}) => {
    const { id, name, maxTotalSubmissions, maxUserSubmissions, type } = category;
    // const onShowDetailsButton = useNavigate();
    return<>
    <Col>
        <Card style={{ width: "18rem" }}>
          <Card.Body>
            <Card.Title>{name}</Card.Title>
            <Card.Text>Type {type}</Card.Text>
            <Card.Text>max totals {maxTotalSubmissions}</Card.Text>
            <Card.Text>max user submissions {maxUserSubmissions}</Card.Text>
            <Button
              variant="primary"
            //   onClick={() => onShowDetailsButton(`/contest/${id}/category`)}
            >
              Details
            </Button>

          </Card.Body>
        </Card>
      </Col>
    </>
}
export default Category;