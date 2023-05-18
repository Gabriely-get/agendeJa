import axios from "axios";

export default function useDisplayCompanyFilialByUserId() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function displayCompanyFilialByUserId(id) {
    let url = `${apiUrl}:5000/agenda/company/user/${id}`;

    const response = await axios.get(url);
    return response.data;
  }

  return {
    displayCompanyFilialByUserId,
  };
}
