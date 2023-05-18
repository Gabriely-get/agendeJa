import axios from "axios";

export default function useDisplayAllEnterpriseJobsById() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function displayAllEnterpriseJobsById(userId, subCatId) {
    let url = `${apiUrl}:5000/agenda/userjob/by/${userId}/${subCatId}`;
    const response = await axios.get(url);
    return response.data;
  }

  return {
    displayAllEnterpriseJobsById,
  };
}
