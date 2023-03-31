import axios from "axios";

export default function useLogin() {
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
      const response = await axios.post(
        "http://ec2-44-200-35-106.compute-1.amazonaws.com:5000/agenda/user/",
        {
          email: email,
          password: password,
          birthday: birthday,
          phone: phone,
          firstName: firstName,
          lastName: lastName,
          cpf: cpf,
        }
      );

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    registerUser,
  };
}
