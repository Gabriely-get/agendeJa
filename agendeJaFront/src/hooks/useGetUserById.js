import axios from "axios";

export default function useGetUserById() {
  const addUser = async (value) => {
    let url =
      "http://ec2-44-200-35-106.compute-1.amazonaws.com:5000/agenda/user/";

    url += encodeURI(value);

    try {
      const response = await axios.get(url);
      console.log("url atual  " + url);

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    addUser,
  };
}
