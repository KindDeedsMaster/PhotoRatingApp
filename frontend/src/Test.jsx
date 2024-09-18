import { useEffect, useState } from "react";
import axiosInstance from "./axiosInstance";


const Test = () => {
    const[data, seData] = useState(null);    
    localStorage.setItem("jwtToken", "");
    useEffect(() => {

    const getData = async() => {
        axiosInstance.get("/api/v1/demo/demo")
        .then((response) => {
            
            
            console.log(response);
            seData(response.data);
        })
        .catch((error) => {
            console.log(error);
        })

    } 
    getData();
   

    }, []);


    return (
      <>
        <div>
          {data ? (
            <div>{JSON.stringify(data)}</div>
          ) : (
            <div>Loading...</div>
          )}
        </div>
      
        </>
      );


}
export default Test;