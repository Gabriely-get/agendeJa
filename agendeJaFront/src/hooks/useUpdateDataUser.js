import axios from "axios";

export default function useUpdateDataUser() {
  const updateUser = async (value, { firstName, lastName, phone }) => {
    let url = `http://ec2-44-202-44-187.compute-1.amazonaws.com:5000/agenda/user/${value}`;

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
