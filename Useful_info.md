# Useful info #

Look into using method 'synchronization'

Memory (disk, ram, etc) is basically a byte [.md](.md) array of size x

The long-term and short-term scheduler are essentially the same class. However, they will act differently according to the parameter that they're passed.

Nothing will access memory directly. Every call to read from or write to memory will be done via the Memory Manager..