import axios from "axios";

export default function useUpdatePortifolioJob() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const updatePortifolioJob = async (idJob, name, price, description) => {
    let url = `${apiUrl}:5000/agenda/userjob/${idJob}`;

    try {
      const response = await axios.put(url, {
        name: name,
        price: price,
        description: description,
      });

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    updatePortifolioJob,
  };
}
