export interface GoogleApiBook {
  selfLink: string;
  volumeInfo: {
    title: string;
    subtitle: string;
    authors: string[];
    publisher: string;
    publishedDate: string;
    description: string;
    industryIdentifiers: [
      {
        type: string;
        identifier: string;
      },
      {
        type: string;
        identifier: string;
      }
      ],
    pageCount: number;
    imageLinks: {
      smallThumbnail: string;
      thumbnail: string;
    }
    language: string;
  };
}
