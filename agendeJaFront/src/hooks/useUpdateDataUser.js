import axios from "axios";

export default function useUpdateDataUser() {
  const updateUser = async (value, { username, phone }) => {
    let url =
      "http://ec2-44-200-35-106.compute-1.amazonaws.com:5000/agenda/user/";

    url += encodeURI(value);
    try {
      const response = await axios.put(url, {
        username: username,
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
