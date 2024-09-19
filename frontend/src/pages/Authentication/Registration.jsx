import axios from "axios";
import { useState } from "react";
import { Button, Form, FormControl } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Registration = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    repeatEmail: "",
    birthYear: "",
    phoneNumber: "",
    password: "",
    repeatPassword: "",
  });
  const [errorMessage, setErrorMessage] = useState("");
  const [isValid, setIsValid] = useState(true);
  const handleForm = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };
  const doFieldsMatch = (field1, field2) => field1 === field2;

  const registerUser = (event) => {
    event.preventDefault();
    axios
      .post("http://localhost:8080/api/v1/auth/register", {
        firstName: formData.firstName,
        lastName: formData.lastName,
        birthYear: formData.birthYear,
        email: formData.email,
        password: formData.password,
      })
      .then((response) => {
        alert("user created");
        navigate("/");
      })
      .catch((error) => {
        setErrorMessage(error.response.data.detail);
      });
  };

  return (
    <Form onSubmit={registerUser}>
      <Form.Group className="mb-3" controlId="formFirstName">
        <Form.Control
          type="text"
          placeholder="Enter first name"
          name="firstName"
          required
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formLastName">
        <Form.Control
          type="text"
          placeholder="Enter last name"
          name="lastName"
          onChange={handleForm}
          required
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formBirthYear">
        <Form.Control
          type="number"
          min={1900}
          max={new Date().getFullYear()}
          placeholder="Enter birth year"
          name="birthYear"
          onChange={handleForm}
          required
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formEmail">
        <Form.Control
          type="email"
          placeholder="Enter email"
          name="email"
          onChange={handleForm}
          required
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formRepeatEmail">
        <Form.Control
          type="email"
          placeholder="Repeat email"
          name="repeatEmail"
          onChange={handleForm}
          isInvalid={formData.email !== formData.repeatEmail}
          required
        />
        <FormControl.Feedback type="invalid">
          "email don't match"
        </FormControl.Feedback>
      </Form.Group>
      <Form.Group className="mb-3" controlId="formPassword">
        <Form.Control
          type="password"
          placeholder="Enter password"
          name="password"
          onChange={handleForm}
          required
        />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formRepeatPassword">
        <Form.Control
          type="password"
          placeholder="Repeat password"
          name="repeatPassword"
          onChange={handleForm}
          isInvalid={formData.password !== formData.repeatPassword}
          required
        />
        <FormControl.Feedback type="invalid">
          "password don't match"
        </FormControl.Feedback>
      </Form.Group>
      <Button
        variant="primary"
        type="submit"
        disabled={
          formData.email !== formData.repeatEmail ||
          formData.password !== formData.repeatPassword
        }
      >
        Register
      </Button>
      {errorMessage && <div style={{ color: "red" }}> {errorMessage}</div>}
    </Form>
  );
};
export default Registration;
