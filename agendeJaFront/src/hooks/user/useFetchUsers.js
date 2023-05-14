import axios from "axios";

export function useFetchUsers() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const searchUser = async (page, valuePage, order) => {
    let url = `${apiUrl}:5000/agenda/user/pag/${page}/${valuePage}/${order}`;

    try {
      const response = await axios.get(url);

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    searchUser,
  };
}
