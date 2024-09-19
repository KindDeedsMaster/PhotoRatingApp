const Logout = () => {
    console.log(localStorage.getItem("jwtToken"));
    localStorage.removeItem("jwtToken");
  
    console.log(localStorage.getItem("jwtToken"));
  };
  
  export default Logout;