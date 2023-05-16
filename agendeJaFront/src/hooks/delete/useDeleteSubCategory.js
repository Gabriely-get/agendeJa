import axios from "axios";

export default function useUpdateSubCategory() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const deleteSubCategory = async (value) => {
    let url = `${apiUrl}:5000/agenda/subcategory/${value}`;

    try {
      const response = await axios.delete(url).catch((response) => {});

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    deleteSubCategory,
  };
}
