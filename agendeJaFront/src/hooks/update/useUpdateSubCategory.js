import axios from "axios";

export default function useUpdateSubCategory() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const updateSubCategory = async (value, name) => {
    let url = `${apiUrl}:5000/agenda/subcategory/${value}`;

    try {
      const response = await axios.put(url, {
        name: name,
      });

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    updateSubCategory,
  };
}
