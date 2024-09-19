import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import { Route, Routes } from "react-router-dom";
import TestingComponent from "./TestingComponent";
import Test from "./Test";
import Registration from "./pages/Authentication/Registration";
import Home from "./pages/Home/Home";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="testing" element={<TestingComponent />} />
        <Route path="/test" element={<Test />} />
        <Route path="/register" element={<Registration />} />
      </Routes>
    </>
  );
}

export default App;
