import axios from "axios";

export default function useDisplaySubCategoryByCatId() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function displaySubCategoryByCatId(value) {
    let url = `${apiUrl}:5000/agenda/subcategory/${value}`;

    const response = await axios.get(url);
    return response.data;
  }

  return {
    displaySubCategoryByCatId,
  };
}
