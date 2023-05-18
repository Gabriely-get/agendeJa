import axios from "axios";

export default function useDisplayJobAllBySubcategoryId() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function displayJobAllBySubcategoryId(id) {
    let url = `${apiUrl}:5000/agenda/job/subcategory/${id}`;
    const response = await axios.get(url);
    return response.data;
  }

  return {
    displayJobAllBySubcategoryId,
  };
}
