import axios from "axios";

export default function useGetUserById() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const addUser = async (user) => {
    let url = `${apiUrl}:5000/agenda/user/${user}`;

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
