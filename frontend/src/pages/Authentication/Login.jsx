import axios from "axios";
import { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });
  const [errorMessage, setErrorMessage] = useState("");

  const handleForm = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };
  const authenticateUser = (event) => {
    event.preventDefault();
    axios
      .post("http://localhost:8080/api/v1/auth/authenticate", {
        email: formData.email,
        password: formData.password,
      })
      .then((response) => {
        const token = response.data.token;
        localStorage.setItem("jwtToken", token);
        console.log(response);
        alert("success");
        navigate("/");
      })
      .catch((error) => {
        setErrorMessage(error.response.data.detail);
        console.log("error message:  ", errorMessage);
        console.log(error.response.data.detail);
        console.log("token error", token);
      });
  };

  return (
    <Form onSubmit={authenticateUser}>
      <Form.Text className="text-muted">
        If you have no account please go to <a href="/register">registration</a>{" "}
        page.
      </Form.Text>
      <Form.Group className="mb-3" controlId="formEmail">
        <Form.Control
          type="email"
          placeholder="Enter email"
          name="email"
          onChange={handleForm}
          required
        />
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

      <Button variant="success" type="submit">
        Login
      </Button>
      {errorMessage && <div style={{ color: "red" }}>{errorMessage}</div>}
    </Form>
  );
};

export default Login;
