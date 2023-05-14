import axios from "axios";

export default function useLogin() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const registerUser = async ({
    email,
    password,
    birthday,
    phone,
    firstName,
    lastName,
    cpf,
  }) => {
    try {
      const response = await axios.post(`${apiUrl}:5000/agenda/user/`, {
        email: email,
        password: password,
        birthday: birthday,
        phone: phone,
        firstName: firstName,
        lastName: lastName,
        cpf: cpf,
      });

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    registerUser,
  };
}
