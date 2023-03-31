import axios from "axios";

export function useFetchUsers() {
  const searchUser = async (page, valuePage, order) => {
    let url =
      "http://ec2-44-200-35-106.compute-1.amazonaws.com:5000/agenda/user/pag/";
    url += encodeURI(page) + "/";
    url += encodeURI(valuePage) + "/";
    url += encodeURI(order);

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
