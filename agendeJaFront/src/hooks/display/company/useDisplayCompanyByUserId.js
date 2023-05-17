import axios from "axios";

export default function useDisplayCompanyByUserId() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function displayCompanyByUserId(id) {
    let url = `${apiUrl}:5000/agenda/portfolio/user/${id}`;

    const response = await axios.get(url);
    return response.data;
  }

  return {
    displayCompanyByUserId,
  };
}
