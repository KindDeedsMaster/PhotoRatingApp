import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import { Route, Routes } from "react-router-dom";
import TestingComponent from "./TestingComponent";
import Test from "./Test";
import Registration from "./pages/Authentication/Registration";
import Home from "./pages/Home/Home";
import Login from "./pages/Authentication/Login";
import Logout from "./pages/Authentication/Logout";
import NavigationBar from "./components/NavigationBar";
import CreateContest from "./pages/Contest/CreateContest";
import Contests from "./pages/Contest/Contests";
import ContestDetails from "./pages/Contest/ContestDetails";
import CategoryDetails from "./pages/Category/CategoryDetails";
import PhotoGallary from "./pages/TestPhoto/PhotoGallary";

function App() {
  return (
    <>
      <NavigationBar />

      <Routes>
        <Route path="/photos" element={<PhotoGallary />} />
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/createContest" element={<CreateContest />} />
        <Route path="/contests" element={<Contests />} />
        <Route path="/contest/:id" element={<ContestDetails />} />
        <Route path="/category/:id" element={<CategoryDetails />} />
        <Route path="testing" element={<TestingComponent />} />
        <Route path="/test" element={<Test />} />
        <Route path="/register" element={<Registration />} />
      </Routes>
    </>
  );
}

export default App;
