import axios from "axios";

export default function useUpdateJob() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const updateJob = async (value, name) => {
    let url = `${apiUrl}:5000/agenda/job/${value}`;

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
    updateJob,
  };
}
