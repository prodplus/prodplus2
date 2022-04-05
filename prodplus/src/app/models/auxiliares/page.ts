export class Page<T> {
  totalPages: number;
  totalElements: number;
  pageable?: {};
  first: boolean;
  sort?: {};
  numberOfElements: number;
  last: boolean;
  size: number;
  content: [];
  number: number;
  empty: boolean;

  constructor() {
    this.totalElements = 0;
    this.totalPages = 0;
    this.first = true;
    this.numberOfElements = 0;
    this.last = true;
    this.size = 0;
    this.content = [];
    this.number = 0;
    this.empty = true;
  }
}
