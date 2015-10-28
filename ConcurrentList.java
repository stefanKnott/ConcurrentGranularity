import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentList{
	private Lock lock;
	private Node head;

	
	public ConcurrentList(){
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		lock = new ReentrantLock();
	}

	public boolean coarse_add(int item){
		Node pred, curr;
		int key = item;
		lock.lock();

		try{
			pred = head;
			curr = pred.next;

			//traverse list to find where to insert new node (using the nodes hash key)
			while(curr.key < key){
				pred = curr;
				curr = curr.next;
			}

			//no duplicates in this set implementation
			if(key == curr.key){
				return false;
			}else{//insert node
			//	System.out.println("Coarse added " + item);

				Node node = new Node(item);
				node.next = curr;
				pred.next = node;
				return true;
			}
		} finally{
			lock.unlock();
		}
	}

	public boolean coarse_remove(int item){
		//System.out.println("Coarse removing "  + item);

		Node pred, curr;
		int key = item;
		lock.lock();

		try{
			pred = head;
			curr = pred.next;

			//traverse..
			while(curr.key < key){
				pred = curr;
				curr = curr.next;
			}

			//item for removal has been found
			if(key == curr.key){
				pred.next = curr.next;
				return true;
			} else{
				//item not found in the list!
				return false;
			}
		} finally{
			lock.unlock();
		}
	}

	public boolean coarse_contains(int item){
		Node pred, curr;
		int key = item;
		lock.lock();

		try{
			pred = head;
			curr = pred.next;

			//traverse..
			while(curr.key < key){
				pred = curr;
				curr = curr.next;
			}

			//item has been found
			if(key == curr.key){
				return true;
			} else{
				//item not found in the list!
				return false;
			}
		} finally{
			lock.unlock();
		}
	}

	public boolean fine_add(int item){
		//System.out.println("Fine adding " + item);

		int key = item;
		head.lock.lock();
		Node pred = head;

		try{
			Node curr = pred.next;
			curr.lock.lock();

			try{
				//traverse list, hand over hand locking
				while(curr.key < key){
					pred.lock.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock.lock();
				}

				//no duplicates in this set
				if(curr.key == key){
					return false;
				}

				Node newNode = new Node(key);
				newNode.next = curr;
				pred.next = newNode;
				return true;
			} finally{
				curr.lock.unlock();
			}
		} finally{
			pred.lock.unlock();
		}
	}

	public boolean fine_remove(int item){
				//System.out.println("Fine removing "  + item);

		Node pred = null, curr = null;
		int key = item;
		head.lock.lock();

		try{
			pred = head;
			curr = pred.next;
			curr.lock.lock();

			try{
				//traverse...
				while(curr.key < key){
					pred.lock.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock.lock();
				}

				//found node for removal
				if(curr.key == key){
					pred.next = curr.next;
					return true;
				}

				return false;
			} finally{
				curr.lock.unlock();
			}
		} finally {
			pred.lock.unlock();
		}
	}


	public boolean fine_contains(int item){
		//System.out.println("FINE CONTAINS.. " + item);
		Node pred = null, curr = null;
		int key = item;
		head.lock.lock();

		try{
			pred = head;
			curr = pred.next;
			curr.lock.lock();

			try{
				//traverse...
				while(curr.key < key){
					pred.lock.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock.lock();
				}

				//found node
				if(curr.key == key){
					return true;
				}

				return false;
			} finally{
				curr.lock.unlock();
			}
		} finally {
			pred.lock.unlock();
		}
	}

	public boolean validate(Node pred, Node curr){
		return !pred.marked && !curr.marked && pred.next == curr;
	}

	public boolean validate2(Node pred, Node curr){
		return !pred.marked && curr.marked && pred.next == curr;
	}

	public boolean lazy_add(int item){
		//System.out.println("LAZY ADDING " + item );

		int key = item;
		while(true){
			Node pred = head;
			Node curr = head.next;

			while(curr.key < key){
				pred = curr;
				curr = curr.next;
			}
			pred.lock.lock();

			try{
				curr.lock.lock();
				try{
					//make sure pred and curr are still in list
					if(validate(pred, curr)){//no duplicates
						if(curr.key == key){
							return false;
						} else{//insert
							Node node = new Node(key);
							node.next = curr;
							pred.next = node;
							return true;
						}
					}
				} finally{
					curr.lock.unlock();
				}
			} finally{
				pred.lock.unlock();
			}
		}
	}

	public boolean lazy_remove(int item){
		//System.out.println("LAZY REMOVING " + item );
		int key = item;
		while(true){
			Node pred = head;
			Node curr = head.next;

			while(curr.key < key){
				pred = curr;
				curr = curr.next;
			}
			pred.lock.lock();

			try{
				curr.lock.lock();
				try{
					//make sure pred and curr are still in list
					if(validate(pred, curr)){
						if(curr.key != key){//not in list
							return false;
						} else{//remove
							curr.marked = true;
							pred.next = curr.next;
							return true;
						}
					}
				} finally{
					curr.lock.unlock();
				}
			} finally{
				pred.lock.unlock();
			}
		}

	}

	public boolean laziest_remove(int item){
		int key = item;
		while(true){
			Node pred = head;
			Node curr = head.next;

			while(curr.key < key){
				pred = curr;
				curr = curr.next;
			}
			pred.lock.lock();

			try{
				curr.lock.lock();
				try{
					//make sure pred and curr are still in list
					if(validate(pred, curr)){
						if(curr.key != key){//not in list
							return false;
						} else{//JUST MARK..NEED TO CALL CLEANUP EVENTUALLY
							curr.marked = true;
							System.out.println("MARKED: " + curr.key);
							return true;
						}
					}
				} finally{
					curr.lock.unlock();
				}
			}finally{
				pred.lock.unlock();
			}
		}
	}

	public boolean clean_up(){
		while(true){
			Node pred = head;
			Node curr = head.next;

			while(pred != null){
				System.out.println("time to clean..");
				pred = curr;
				if(pred.next != null){
					curr = curr.next;
				}else if(pred != null && pred.next == null){//last node in list...if marked set to null
					if(pred.marked){
						pred = null;
						return true;
					}else{return false;}//nothing to remove since only node (pred) is not marked
				}else{return false;}//list is empty, nothing to clean up
					//bring this if statement in!!!! WRITE NEW VALIDATE METHOD TO VALIDATE THAT CURR IS MARKED AND PRED IS NOT
					pred.lock.lock();
					try{
						curr.lock.lock();
						try{
						//make sure pred and curr are still in list
							if(curr.marked){//if marked..lock, validate and remove
								if(validate2(pred, curr)){//remove
									pred.next = curr.next;
									System.out.println("CLEANUP HAPPENED" + curr.key + " removed");
									return true;
								}else{ return false;}
							}
						} finally{
							curr.lock.unlock();
						}
					} finally{
						pred.lock.unlock();
					}			
			}
		}
	}

	public boolean lazy_contains(int item){
		//System.out.println("lazy contains.. " + item);
		int key = item;
		Node curr = head;
		while(curr.key < key)
			curr = curr.next;
		return curr.key == key && !curr.marked;
	}
}

