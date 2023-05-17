import axios from "axios";

export default function useDeleteSubcategoryPortifolio() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const deleteSubcategoryPortifolio = async (value, userId, subcategoryId) => {
    let url = `${apiUrl}:5000/agenda/portfolio/subcategory/${value}`;

    try {
      const response = await axios.put(url, {
        userId: userId,
        subCategoryId: subcategoryId,
      });

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    deleteSubcategoryPortifolio,
  };
}
