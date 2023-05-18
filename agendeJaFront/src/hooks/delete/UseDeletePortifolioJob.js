import axios from "axios";

export default function useDeletePortifolioJob() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const deletePortifolioJob = async (value) => {
    let url = `${apiUrl}:5000/agenda/userjob/${value}`;

    try {
      const response = await axios.delete(url).catch((response) => {});

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    deletePortifolioJob,
  };
}
