import { Button, Card, Col } from "react-bootstrap";
import { propTypes } from "react-bootstrap/esm/Image";
import { useNavigate } from "react-router-dom";

const Contest = ( {contest} ) => {
  const { id, name, maxTotalSubmissions, startDate, endDate } = contest;
  const onShowDetailsButton = useNavigate();

//   Contest.propTypes = {
//     contest: propTypes.object,
//   };

  return <>
        <Col>
        <Card style={{ width: "18rem" }}>
          <Card.Body>
            <Card.Title>{name}</Card.Title>
            <Card.Text>Max submissions {maxTotalSubmissions}</Card.Text>
            <Card.Text>start date {startDate}</Card.Text>
            <Card.Text>end date {endDate}</Card.Text>
            <Button
              variant="primary"
              onClick={() => onShowDetailsButton(`/contest/${id}`)}
            >
              Details
            </Button>

          </Card.Body>
        </Card>
      </Col>
  </>;
};
export default Contest;
