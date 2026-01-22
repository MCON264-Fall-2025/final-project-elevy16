# Event Planner Mini

This project demonstrates practical use of data structures:
linked lists, stacks, queues, maps, trees, sorting, and searching.

## Data Structure Choices

### GuestListManager

**LinkedList for the main guest list**
I used a LinkedList to keep the guest list in order and makes it easy to add/remove guests without the overhead of ArrayList resizing.

**HashMap for guest lookup**
I used a HashMap to make it fast to find a guest by name. The HashMap gives O(1) lookups instead of searching through the whole list every time.

### VenueSelector

**ArrayList for filtering and sorting**
I filter the venues down to valid options (within budget and capacity), then sort them. ArrayList works well here because I need to sort the list anyway.

**Custom Comparator for sorting**
The comparator sorts by cost first (cheapest wins), then by capacity if there's a tie (smallest wins).

### SeatingPlanner

**HashMap to group guests by tag**
Each group tag (family, friends, etc.) maps to a Queue of guests with that tag. HashMap lookup is O(1), so grouping guests is fast.

**Queue for each group**
Within each group, guests are seated in FIFO order - first added gets seated first. This keeps things fair and maintains the order guests were added.

### TaskManager

**Queue for upcoming tasks**
Tasks execute in FIFO order - first task added gets executed first. So I use Queue.

**Stack for completed tasks**
Undo needs LIFO behavior - undo the most recent task first. Stack gives us this automatically with push/pop operations.


## Algorithms
**Sorting:** VenueSelector uses Java's Timsort (Collections.sort) with a custom Comparator. This is O(n log n).

**Searching:** GuestListManager uses HashMap lookup for O(1) average case search by name.


## Complexity Analysis

**Finding a guest:** O(1)  
HashMap lookup is constant time on average.

**Selecting a venue:** O(n log n)  
Filtering is O(n), sorting is O(n log n)

**Generating seating:** O(g) where g = number of guests  
We iterate through guests once to group them, then once more to assign seats.

## Testing

All core classes have JUnit tests covering:
- Normal operations
- Edge cases (empty collections, null returns)
- Multiple operations in sequence
- Boundary conditions
