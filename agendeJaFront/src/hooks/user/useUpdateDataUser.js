import axios from "axios";

export default function useUpdateDataUser() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const updateUser = async (value, { firstName, lastName, phone }) => {
    let url = `${apiUrl}:5000/agenda/user/${value}`;

    try {
      const response = await axios.put(url, {
        firstName: firstName,
        lastName: lastName,
        phone: phone,
      });

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    updateUser,
  };
}
