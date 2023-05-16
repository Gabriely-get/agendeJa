import axios from "axios";

export default function useUpdateCategory() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const updateCategory = async (value, name) => {
    let url = `${apiUrl}:5000/agenda/category/${value}`;

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
    updateCategory,
  };
}
