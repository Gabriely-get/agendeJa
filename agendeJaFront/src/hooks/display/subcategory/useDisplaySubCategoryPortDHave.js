import axios from "axios";

export default function useDisplaySubCategoryPortDHave() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function displaySubCategoryPortDHave(value) {
    let url = `${apiUrl}:5000/agenda/portfolio/subcategories/${value}`;

    const response = await axios.get(url);
    return response.data;
  }

  return {
    displaySubCategoryPortDHave,
  };
}
