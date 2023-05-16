import axios from "axios";

export default function useUpdateCategory() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const deleteCategory = async (value) => {
    let url = `${apiUrl}:5000/agenda/category/${value}`;

    try {
      const response = await axios.delete(url).catch((response) => {});

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    deleteCategory,
  };
}
