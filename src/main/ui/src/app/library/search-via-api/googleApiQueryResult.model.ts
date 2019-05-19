import {GoogleApiBook} from './googleApiBook.model';

export interface GoogleApiQueryResult {
  totalItems: number;
  items: GoogleApiBook[];
}
