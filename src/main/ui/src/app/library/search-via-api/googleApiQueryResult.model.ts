import {GoogleApiBook} from './googleApiBook.model';

export interface GoogleApiQueryResult {
  kind: string;
  totalItems: number;
  items: GoogleApiBook[];
}
