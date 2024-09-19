import { useState } from "react";
import axiosInstance from "../../axiosInstance";
import { Button, Form } from "react-bootstrap";

const CreateContest = () => {
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    maxTotalSubmissions: "",
    maxUserSubmissions: "",
    startDate: "",
    endDate: "",
  });
  const [errorMessage, setErrorMessage] = useState("");

  const handleForm = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const addContest = (event) => {
    event.preventDefault();
    axiosInstance
      .post("api/v1/contests", {
        name: formData.title,
        description: formData.description,
        maxTotalSubmissions: formData.maxTotalSubmissions,
        maxUserSubmissions: formData.maxUserSubmissions,
        startDate: new Date(formData.startDate).toISOString(),
        endDate: new Date(formData.endDate).toISOString(),
      })
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log("error", error);
        setErrorMessage(error.response.data.detail);
      });
  };

  return (
    <Form onSubmit={addContest}>
      <Form.Group className="mb-3" controlId="formTitle">
        <Form.Control
          type="text"
          name="title"
          placeholder="Enter contest title"
          onChange={handleForm}
          required
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formDescription">
        <Form.Control
          type="text"
          placeholder="Enter description"
          name="description"
          onChange={handleForm}
          required
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formMaxTotalSubmissions">
        <Form.Control
          type="number"
          placeholder="Enter maximum submissions"
          name="maxTotalSubmissions"
          onChange={handleForm}
          min={1}
          max={9999}
          required
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formMaxUserSubmissions">
        <Form.Control
          type="number"
          placeholder="Enter maximum user submissions"
          name="maxUserSubmissions"
          onChange={handleForm}
          min={1}
          max={9999}
          required
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="contestStartDate">
        <Form.Text id="contestEndDate" muted>
          Contest start date
        </Form.Text>
        <Form.Control
          type="date"
          name="startDate"
          onChange={handleForm}
          required
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formEndDate">
        <Form.Text id="contestEndDate" muted>
          Contest end date
        </Form.Text>
        <Form.Control
          type="date"
          name="endDate"
          onChange={handleForm}
          required
        />
      </Form.Group>
      <Button variant="success" type="submit">
        Create Contest
      </Button>
      {errorMessage && <div style={{ color: "red" }}>{errorMessage}</div>}
    </Form>
  );
};
export default CreateContest;
