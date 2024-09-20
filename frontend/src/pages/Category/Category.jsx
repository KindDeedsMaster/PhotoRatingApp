import { Button, Card, Col } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Category = ({category}) => {    
    const onShowDetailsButton = useNavigate();
    const { id, name, maxTotalSubmissions, maxUserSubmissions, type } = category;

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
              onClick={() => onShowDetailsButton(`/category/${id}`)}
            >
              Details
            </Button>

          </Card.Body>
        </Card>
      </Col>
    </>
}
export default Category;