import axios from "axios";

export function useFetchUsers() {
  const searchUser = async (page, valuePage, order) => {
    let url = `http://ec2-44-202-44-187.compute-1.amazonaws.com:5000/agenda/user/pag/${page}/${valuePage}/${order}`;

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
