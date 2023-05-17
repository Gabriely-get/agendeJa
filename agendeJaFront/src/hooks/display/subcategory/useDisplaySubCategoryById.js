import axios from "axios";

export default function useDisplaySubCategoryById() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function displaySubCategoryById(value) {
    let url = `${apiUrl}:5000/agenda/subcategory/by/${value}`;

    const response = await axios.get(url);
    return response.data;
  }

  return {
    displaySubCategoryById,
  };
}
