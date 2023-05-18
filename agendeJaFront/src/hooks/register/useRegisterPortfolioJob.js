import axios from "axios";

export default function useRegisterPortifolioJob() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function registerPortifolioJob(
    name,
    price,
    portfolioId,
    jobId,
    description
  ) {
    let url = `${apiUrl}:5000/agenda/userjob/`;

    const response = await axios.post(url, {
      name: name,
      price: price,
      portfolioId: portfolioId,
      jobId: jobId,
      description: description,
    });
    return response.data;
  }

  return {
    registerPortifolioJob,
  };
}
