import axios from "axios";

export default function useGetUserById() {
  const addUser = async (user) => {
    let url = `http://ec2-44-202-44-187.compute-1.amazonaws.com:5000/agenda/user/${user}`;

    try {
      const response = await axios.get(url);

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    addUser,
  };
}
