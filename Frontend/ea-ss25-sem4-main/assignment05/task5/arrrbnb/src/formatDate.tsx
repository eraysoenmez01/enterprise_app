export function formatDate(dateString: string): string {
    const date = new Date(dateString);
  
    if (isNaN(date.getTime())) return "";
  
    return new Intl.DateTimeFormat("en-US", {
      month: "short",
      day: "numeric",
      year: "numeric",
    }).format(date);
  }